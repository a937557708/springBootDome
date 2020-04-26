package com.tjr.utils;


import de.hunsicker.io.Copy;
import de.hunsicker.io.FileBackup;
import de.hunsicker.io.FileFormat;
import de.hunsicker.io.IoHelper;
import de.hunsicker.jalopy.VersionMismatchException;
import de.hunsicker.jalopy.language.CodeInspector;
import de.hunsicker.jalopy.language.CompositeFactory;
import de.hunsicker.jalopy.language.JavaRecognizer;
import de.hunsicker.jalopy.language.antlr.JavaNode;
import de.hunsicker.jalopy.printer.NodeWriter;
import de.hunsicker.jalopy.printer.PrinterFactory;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.Environment;
import de.hunsicker.jalopy.storage.History;
import de.hunsicker.jalopy.storage.Loggers;
import de.hunsicker.jalopy.storage.Environment.Variable;
import de.hunsicker.jalopy.storage.History.ChecksumCharArrayWriter;
import de.hunsicker.jalopy.storage.History.Method;
import de.hunsicker.jalopy.storage.History.Policy;
import de.hunsicker.util.Version;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public final class Jalopy {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int FILE_INPUT = 1;
    private static final int FILE_OUTPUT = 2;
    private static final int ILLEGAL = 0;
    private static final int READER_INPUT = 16;
    private static final int STRING_INPUT = 4;
    private static final int STRING_OUTPUT = 8;
    private static final int WRITER_OUTPUT = 32;
    private static Version _version = Version.valueOf(loadVersionString());
    private static final int FILE_FILE = 3;
    private static final int FILE_STRING = 9;
    private static final int FILE_WRITER = 33;
    private static final int STRING_FILE = 6;
    private static final int STRING_STRING = 12;
    private static final int STRING_WRITER = 36;
    private static final int READER_FILE = 18;
    private static final int READER_STRING = 24;
    private static final int READER_WRITER = 48;
    private Checksum _inputFileChecksum;
    private CodeInspector _inspector;
    private File _backupDir;
    private File _backupFile;
    private File _destination;
    private File _inputFile;
    private File _outputFile;
    private FileFormat _inputFileFormat;
    private FileFormat _outputFileFormat;
    private JavaNode _tree;
    private final JavaRecognizer _recognizer;
    private Map _issues;
    private Method _historyMethod;
    private Policy _historyPolicy;
    private Reader _inputReader;
    private final Jalopy.SpyAppender _spy;
    Jalopy.State _state;
    private String _encoding;
    private String _inputString;
    private String _packageName;
    private StringBuffer _outputString;
    private StringWriter _outputStringBuffer;
    private Writer _outputWriter;
    private Object[] _args;
    private boolean _force;
    private boolean _holdBackup;
    private boolean _inspect;
    private int _backupLevel;
    private int _mode;
    private long _now;
    private long _timeParsing;
    private long _timePrinting;
    private long _timeTransforming;
    private static CompositeFactory _factory = null;

    public Jalopy() {
        this._historyMethod = Method.TIMESTAMP;
        this._historyPolicy = Policy.DISABLED;
        this._state = Jalopy.State.UNDEFINED;
        this._args = new Object[5];
        this.initConventionDefaults();
        this._issues = new HashMap(30);
        if (_factory == null) {
            _factory = new CompositeFactory();
        }

        this._recognizer = new JavaRecognizer(_factory);
        this._inspector = new CodeInspector(this._issues);
        this._spy = new Jalopy.SpyAppender();
        Loggers.ALL.addAppender(this._spy);
    }

    public static void setConvention(File file) throws IOException {
        Convention.importSettings(file);
    }

    public static void setConvention(URL url) throws IOException {
        Convention.importSettings(url);
    }

    public static void setConvention(String location) throws IOException {
        if (!location.startsWith("http://") && !location.startsWith("www.")) {
            setConvention(new File(location));
        } else {
            setConvention(new URL(location));
        }

    }

    public void setEncoding(String encoding) {
        if (encoding != null) {
            try {
                new String(EMPTY_BYTE_ARRAY, encoding);
            } catch (UnsupportedEncodingException var3) {
                throw new IllegalArgumentException("invalid encoding specified -- " + encoding);
            }
        }

        this._encoding = encoding;
    }

    public void setFileFormat(FileFormat format) {
        this._outputFileFormat = format;
    }

    public void setFileFormat(String format) {
        this._outputFileFormat = FileFormat.valueOf(format);
    }

    public void setForce(boolean force) {
        this._force = force;
    }

    public void setHistoryMethod(Method method) {
        this._historyMethod = method;
    }

    public void setHistoryPolicy(Policy policy) {
        this._historyPolicy = policy;
    }

    public void setInspect(boolean enabled) {
        this._inspect = enabled;
    }

    public boolean isInspect() {
        return this._inspect;
    }

    public static Version getVersion() {
        return _version;
    }

    public void setOutput(Writer output) {
        this._outputWriter = output;
        this._mode += 32;
    }

    public String getProfileTimes() {
        long whole = this._timeParsing + this._timeTransforming + this._timePrinting;
        if (whole > 0L) {
            StringBuffer buf = new StringBuffer(100);
            buf.append(this._timeParsing);
            buf.append('(');
            buf.append(this._timeParsing * 100L / whole);
            buf.append("%) ");
            buf.append(this._timeTransforming);
            buf.append('(');
            buf.append(this._timeTransforming * 100L / whole);
            buf.append("%) ");
            buf.append(this._timePrinting);
            buf.append('(');
            buf.append(this._timePrinting * 100L / whole);
            buf.append("%)");
            return buf.toString();
        } else {
            return "";
        }
    }

    public JavaRecognizer getRecognizer() {
        return this._recognizer;
    }

    public Jalopy.State getState() {
        return this._state;
    }

    public static void checkCompatibility(String packageName) throws VersionMismatchException {
    }

    public void setBackup(boolean backup) {
        this._holdBackup = backup;
    }

    public void setBackupDirectory(File directory) {
        if (!directory.isAbsolute()) {
            directory = new File(Convention.getProjectSettingsDirectory(), directory.getPath());
        }

        IoHelper.ensureDirectoryExists(directory.getAbsoluteFile());
        if (directory.exists() && directory.isDirectory()) {
            this._backupDir = directory.getAbsoluteFile();
        } else {
            throw new IllegalArgumentException("invalid directory -- " + directory);
        }
    }

    public void setBackupDirectory(String directory) {
        this.setBackupDirectory(new File(directory));
    }

    public File getBackupDirectory() {
        return this._backupDir;
    }

    public void setBackupLevel(int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level has to be >= 0");
        } else {
            this._backupLevel = level;
            if (level == 0) {
                this.setBackup(false);
            }

        }
    }

    public void setDestination(File destination) {
        if (destination == null || destination.exists() && !destination.isDirectory()) {
            throw new IllegalArgumentException("no valid directory -- " + destination);
        } else {
            if (!destination.exists()) {
                if (!destination.mkdirs()) {
                    throw new RuntimeException("could not create destination directory -- " + destination);
                }

                Object[] args = new Object[]{destination};
            }

            this._destination = destination;
        }
    }

    public void setInput(String input, String path) {
        if (input == null) {
            throw new NullPointerException();
        } else if (path == null) {
            throw new NullPointerException();
        } else {
            this._inputFile = new File(path);
            this._inputFileChecksum = null;
            this._inputString = input;
            this._inputReader = new BufferedReader(new StringReader(input));
            if (!this.hasInput()) {
                this._mode += 4;
            }

        }
    }

    public void setInput(InputStream input, String path) {
        if (input != null) {
            if (path == null) {
                throw new IllegalArgumentException("no path given");
            } else {
                File file = new File(path);
                if ((!file.exists() || !file.isFile()) && System.in != input) {
                    throw new IllegalArgumentException("invalid path given -- " + path);
                } else {
                    this._inputFile = new File(path);
                    this._inputFileChecksum = null;
                    this._inputReader = new BufferedReader(new InputStreamReader(input));
                    if (!this.hasInput()) {
                        this._mode += 16;
                    }

                }
            }
        }
    }

    public void setInput(Reader input, String path) {
        if (path == null) {
            throw new IllegalArgumentException("no path given");
        } else if (input != null) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                this._inputFile = new File(path);
                this._inputFileChecksum = null;
                this._inputReader = input;
                if (!this.hasInput()) {
                    this._mode += 16;
                }

            } else {
                throw new IllegalArgumentException("invalid path given -- " + path);
            }
        }
    }

    public void setInput(File input) throws FileNotFoundException {
        this._inputReader = getBufferedReader(input, this._encoding);
        this._inputFile = input.getAbsoluteFile();
        this._inputFileChecksum = null;
        if (!this.hasInput()) {
            ++this._mode;
        }

    }

    public void setOutput(File output) {
        this._outputFile = output;
        this._mode += 2;
    }

    public void setOutput(StringBuffer output) {
        this._outputString = output;
        this._outputStringBuffer = new StringWriter();
        this._outputWriter = new BufferedWriter(this._outputStringBuffer);
        this._mode += 8;
    }

    public void cleanupBackupDirectory() {
        if (!this._holdBackup) {
            this.cleanupDirectory(this._backupDir);
        }

    }

    public boolean format() {
        return this.format(true);
    }

    public boolean format(boolean runCleanup) {
        JavaNode tree = null;
        boolean formatSuccess = false;
        if (!this.hasInput()) {
            throw new IllegalStateException("no input source specified");
        } else {
            try {
                boolean ex;
                if (!this.isDirty()) {
                    this._args[0] = this._inputFile;
                    this._state = Jalopy.State.OK;
                    this.cleanup();
                    ex = false;
                    return ex;
                }

                if (this._state == Jalopy.State.PARSED && this._state == Jalopy.State.INSPECTED) {
                    tree = this._tree;
                } else {
                    tree = this.parse();
                    if (this._state == Jalopy.State.ERROR) {
                        this.cleanup();
                        ex = false;
                        return ex;
                    }
                }

                formatSuccess = this.format(tree, this._packageName, this._inputFileFormat, false);
            } catch (Throwable var9) {
                this._state = Jalopy.State.ERROR;
                var9.printStackTrace();
                this._args[0] = this._inputFile;
                this._args[1] = var9.getMessage() == null ? var9.getClass().getName() : var9.getMessage();
            } finally {
                _factory.clear();
            }

            return formatSuccess;
        }
    }

    public void inspect() {
        JavaNode tree = null;
        if (this._state != Jalopy.State.PARSED) {
            try {
                tree = this.parse();
                if (this._state == Jalopy.State.ERROR) {
                    return;
                }
            } catch (Throwable var3) {
                this._state = Jalopy.State.ERROR;
                this._args[0] = this._inputFile;
                this._args[1] = var3.getMessage() == null ? var3.getClass().getName() : var3.getMessage();
            }
        } else {
            tree = this._tree;
        }

        this.inspect(tree);
    }

    public void inspect(JavaNode tree) {
        if (tree == null) {
            throw new NullPointerException();
        } else {
            switch(tree.getType()) {
                case 67:
                    long start = 0L;
                    if (Loggers.IO.isDebugEnabled()) {
                        start = System.currentTimeMillis();
                        this._args[0] = this._inputFile;
                    }

                    this._inspector.inspect(tree, this._outputFile != null ? this._outputFile : this._inputFile);
                    if (Loggers.IO.isDebugEnabled()) {
                        long stop = System.currentTimeMillis();
                        Loggers.IO.debug(this._inputFile + ":0:0:inspecting took " + (stop - start));
                    }

                    if (this._state != Jalopy.State.ERROR) {
                        this._state = Jalopy.State.INSPECTED;
                    }

                    return;
                default:
                    throw new IllegalArgumentException("not a root node -- " + tree);
            }
        }
    }

    public JavaNode parse() {
        long start = 0L;
        this._state = Jalopy.State.RUNNING;
        if (Loggers.IO.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        JavaNode var11;
        try {
            switch(this._mode) {
                case 1:
                case 3:
                case 9:
                case 33:
                    this._args[0] = this._inputFile;
                    this._recognizer.parse(this._inputReader, this._inputFile.getAbsolutePath());
                    break;
                case 2:
                case 5:
                case 7:
                case 8:
                case 10:
                case 11:
                case 13:
                case 14:
                case 15:
                case 17:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 34:
                case 35:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                default:
                    throw new IllegalStateException("no input source specified");
                case 4:
                case 6:
                case 12:
                case 16:
                case 18:
                case 24:
                case 36:
                case 48:
                    this._args[0] = this._inputFile;
                    this._recognizer.parse(this._inputReader, this._inputFile.getAbsolutePath());
            }

            JavaNode tree;
            if (this._state == Jalopy.State.ERROR) {
                tree = null;
                return tree;
            }

            if (Loggers.IO.isDebugEnabled()) {
                long stop = System.currentTimeMillis();
                Loggers.IO.debug(this._inputFile.getAbsolutePath() + ":0:0:parsing took " + (stop - start));
                this._timeParsing += stop - start;
            }

            if (this._state != Jalopy.State.ERROR) {
                this._state = Jalopy.State.PARSED;
            }

            tree = null;
            if (Loggers.IO.isDebugEnabled()) {
                Loggers.IO.debug((this._outputFile != null ? this._outputFile : this._inputFile) + ":0:0:transform");
                start = System.currentTimeMillis();
                tree = (JavaNode)this._recognizer.getParseTree();
                long stop = System.currentTimeMillis();
                this._timeTransforming += stop - start;
                Loggers.IO.debug((this._outputFile != null ? this._outputFile : this._inputFile) + ":0:0:transforming took " + (stop - start));
            } else {
                tree = (JavaNode)this._recognizer.getParseTree();
            }

            this._tree = tree;
            this._inputFileFormat = this._recognizer.getFileFormat();
            this._packageName = this._recognizer.getPackageName();
            var11 = tree;
        } finally {
            this.cleanupRecognizer();
        }

        return var11;
    }

    public void reset() {
        this.cleanup();
        this.initConventionDefaults();
    }

    void resetTimers() {
        this._timeParsing = 0L;
        this._timePrinting = 0L;
        this._timeTransforming = 0L;
    }

    private void cleanupDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for(int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    if (files[i].list().length > 0) {
                        this.cleanupDirectory(files[i]);
                    } else if (files[i].delete()) {
                        File parent = directory.getParentFile();
                        if (!directory.equals(this._backupDir)) {
                            this.cleanupDirectory(parent);
                        }
                    }
                }
            }
        }

    }

    private boolean format(JavaNode tree, String packageName, FileFormat format, boolean check) {
        String defaultEncoding = null;

        boolean ex;
        try {
            this._args[0] = this._inputFile;
            if (check && !this.isDirty()) {
                this._state = Jalopy.State.OK;
                ex = false;
                return ex;
            }

            if (this._encoding != null) {
                defaultEncoding = System.getProperty("file.encoding");
                System.setProperty("file.encoding", this._encoding);
            }

            if (this._inspect) {
                this.inspect(tree);
            }

            this.print(tree, packageName, format);
            if (this._inspect) {
                Object[] issues = new Object[6];
                Iterator i = this._issues.entrySet().iterator();

                while(i.hasNext()) {
                    Entry entry = (Entry)i.next();

                    JavaNode node;
                    for(node = (JavaNode)entry.getKey(); node != null && node.getParent() != null && node.newLine == 0; node = node.getParent()) {
                    }

                    Object message = entry.getValue();
                    issues[0] = this._inputFile.getAbsolutePath();
                    issues[1] = new Integer(node.newLine);
                    issues[2] = new Integer(node.newColumn);
                    issues[3] = message.toString();
                    issues[4] = new Integer(node.getStartLine());
                    issues[5] = node;
                }
            }

            if (this._state != Jalopy.State.ERROR) {
                if (this._outputStringBuffer != null) {
                    this._outputString.setLength(0);
                    this._outputString.append(this._outputStringBuffer.toString());
                }

                if (this._outputFile != null) {
                    this._inputReader.close();
                    if (this._outputWriter != null) {
                        this._outputWriter.close();
                        this._outputFile.setLastModified(this._now);
                    }

                    if (this._state == Jalopy.State.PARSED || this._state == Jalopy.State.INSPECTED || this._state == Jalopy.State.RUNNING) {
                        this._state = Jalopy.State.OK;
                    }
                }

                if (!this._holdBackup && this._backupFile != null && this._backupFile.exists()) {
                    this._backupFile.delete();
                    if (Loggers.IO.isDebugEnabled()) {
                        this._args[0] = this._inputFile;
                        this._args[1] = this._backupFile;
                        return this._state != Jalopy.State.ERROR;
                    }
                }

                return this._state != Jalopy.State.ERROR;
            }

            this.restore(this._inputFile, this._backupFile);
            ex = false;
        } catch (Throwable var15) {
            var15.printStackTrace();
            this._state = Jalopy.State.ERROR;
            this._args[0] = this._inputFile;
            this._args[1] = var15.getMessage() == null ? var15.getClass().getName() : var15.getMessage();
            this.restore(this._inputFile, this._backupFile);
            return this._state != Jalopy.State.ERROR;
        } finally {
            if (defaultEncoding != null) {
                System.setProperty("file.encoding", defaultEncoding);
            }

            this.cleanup();
        }

        return ex;
    }

    private boolean hasInput() {
        switch(this._mode) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 9:
            case 12:
            case 16:
            case 18:
            case 24:
            case 33:
            case 36:
            case 48:
                return true;
            case 2:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
                return false;
        }
    }

    private static String loadVersionString() {
        return "1.0b10";
    }

    private boolean isChecksum() {
        boolean result = this._outputFile != null && this._historyPolicy == Policy.FILE && (this._historyMethod == Method.CRC32 || this._historyMethod == Method.ADLER32);
        return result;
    }

    private File getDestinationFile(File destination, String packageName, String filename) throws IOException {
        StringBuffer buf = new StringBuffer(90);
        buf.append(destination);
        buf.append(File.separator);
        buf.append(packageName.replace('.', File.separatorChar));
        File test = new File(buf.toString());
        if (!test.exists()) {
            if (!test.mkdirs()) {
                throw new IOException("could not create target directory -- " + buf);
            }

            if (Loggers.IO.isDebugEnabled()) {
                Loggers.IO.debug("directory " + test + " created");
            }
        }

        buf.append(File.separator);
        buf.append(filename);
        return new File(buf.toString());
    }

    private boolean isDirty() throws IOException {
        if (this._force) {
            return true;
        } else if ((this._mode & 1) == 0) {
            return this._inputReader != null;
        } else if ((this._inputFile == null || this._inputFile.exists()) && this._inputFile.length() != 0L) {
            int count;
            if (this._historyPolicy == Policy.FILE) {
                de.hunsicker.jalopy.storage.History.Entry entry = History.getInstance().get(this._inputFile);
                if (entry != null) {
                    if (this._historyMethod == Method.TIMESTAMP) {
                        return entry.getModification() < this._inputFile.lastModified();
                    } else if (this._inputFileChecksum == null) {
                        if (this._historyMethod == Method.CRC32) {
                            this._inputFileChecksum = new CRC32();
                        } else if (this._historyMethod == Method.ADLER32) {
                            this._inputFileChecksum = new Adler32();
                        }

                        BufferedInputStream in = null;

                        try {
                            in = new BufferedInputStream(new FileInputStream(this._inputFile));
                            byte[] buffer = new byte[8192];
                            count = 0;

                            do {
                                this._inputFileChecksum.update(buffer, 0, count);
                                count = in.read(buffer, 0, buffer.length);
                            } while(count != -1);
                        } finally {
                            if (in != null) {
                                in.close();
                            }

                        }

                        return this._inputFileChecksum.getValue() != entry.getModification();
                    } else {
                        return this._inputFileChecksum.getValue() != entry.getModification();
                    }
                } else {
                    return true;
                }
            } else if (this._historyPolicy == Policy.COMMENT) {
                BufferedReader in = null;

                try {
                    in = getBufferedReader(this._inputFile, this._encoding);
                    String line = in.readLine().trim();
                    in.close();
                    if (line.startsWith("// %") && line.endsWith("%") && line.indexOf("modified") == -1) {
                        int start = line.indexOf(37) + 1;
                        count = line.indexOf(58);
                        long lastmod = Long.parseLong(line.substring(start, count));
                        if (lastmod >= this._inputFile.lastModified()) {
                            if (this._destination != null) {
                                String packageName = line.substring(count + 1, line.length() - 1);
                                this.copyInputToOutput(this._inputFile, this._destination, packageName, lastmod);
                            }

                            boolean var20 = false;
                            return var20;
                        }
                    }

                    boolean var18 = true;
                    return var18;
                } finally {
                    if (in != null) {
                        in.close();
                    }

                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private String getLineSeparator(FileFormat fileFormat, FileFormat detectedFileFormat) {
        return fileFormat == FileFormat.AUTO ? detectedFileFormat.toString() : fileFormat.getLineSeparator();
    }

    private void setLocalVariables(Environment environment, File file, String packageName, String fileFormat, int indentSize) {
        environment.set(Variable.FILE_NAME.getName(), file.getName());
        environment.set(Variable.FILE.getName(), file.getAbsolutePath());
        environment.set(Variable.PACKAGE.getName(), "".equals(packageName) ? "default package" : packageName);
        environment.set(Variable.FILE_FORMAT.getName(), fileFormat);
        environment.set(Variable.TAB_SIZE.getName(), String.valueOf(indentSize));
        DateFormat df = DateFormat.getDateTimeInstance();
        environment.set(Variable.DATE.getName(), new Date());
        String className = file.getName();
        className = className.substring(0, className.length() - 5);
        environment.set(Variable.CLASS_NAME.getName(), className);
    }

    private boolean isWritable(File file) {
        if (file != null) {
            return !file.exists() ? true : file.canWrite();
        } else {
            return false;
        }
    }

    private void addCommentHistoryEntry(String packageName, NodeWriter out) throws IOException {
        if (this._historyPolicy == Policy.COMMENT && this._inputFile != null) {
            StringBuffer buf = new StringBuffer(40);
            buf.append("// %");
            buf.append(this._now);
            buf.append(':');
            buf.append(packageName);
            buf.append('%');
            out.print(buf.toString(), 176);
            out.printNewline();
        }

    }

    private void addFileHistoryEntry(String packageName, ChecksumCharArrayWriter checksumWriter) throws IOException {
        if (this._historyPolicy == Policy.FILE && this._inputFile != null) {
            if (this.isChecksum()) {
                History.getInstance().add(this._inputFile, packageName, checksumWriter.getChecksum().getValue());
            } else {
                History.getInstance().add(this._inputFile, packageName, this._now);
            }
        }

    }

    private void cleanup() {
        try {
            if (this._inputReader != null) {
                this._inputReader.close();
                this._inputReader = null;
            }
        } catch (IOException var3) {
        }

        try {
            if (this._outputWriter != null) {
                this._outputWriter.close();
                this._outputWriter = null;
            }
        } catch (IOException var2) {
        }

        this._mode = 0;
        this._issues.clear();
        this._inputFile = null;
        this._inputString = null;
        this._outputStringBuffer = null;
        this._outputString = null;
        this._outputFile = null;
        this._backupFile = null;
        this._packageName = null;
        this._inputFileFormat = null;
        this._tree = null;
        this.cleanupRecognizer();
    }

    private void cleanupRecognizer() {
        this._recognizer.reset();
    }

    private void copyInputToOutput(File inputFile, File destination, String packageName, long lastmod) throws IOException {
        File file = this.getDestinationFile(destination, packageName, inputFile.getName());
        if (!file.exists() || file.lastModified() != lastmod) {
            Copy.file(inputFile, file, true);
            file.setLastModified(lastmod);
            this._args[0] = inputFile;
            this._args[1] = file.getAbsolutePath();
        }

    }

    private File createBackup(String packageName) throws IOException {
        File directory;
        switch(this._mode) {
            case 3:
                if (this._inputFile.equals(this._outputFile)) {
                    IoHelper.ensureDirectoryExists(this._backupDir);
                    directory = new File(this._backupDir + File.separator + packageName.replace('.', File.separatorChar));
                    File backupFile = FileBackup.create(this._inputFile, directory, this._backupLevel);
                    if (Loggers.IO.isDebugEnabled()) {
                        this._args[1] = backupFile;
                    }

                    return backupFile;
                }
                break;
            case 6:
            case 12:
            case 36:
                if (this._inputFile.exists()) {
                    IoHelper.ensureDirectoryExists(this._backupDir);
                    directory = new File(this._backupDir + File.separator + packageName.replace('.', File.separatorChar));
                    String filename = this._inputFile.getName();
                    File backupFile = FileBackup.create(this._inputString, filename, directory, this._backupLevel);
                    if (Loggers.IO.isDebugEnabled()) {
                        this._args[1] = backupFile;
                    }

                    return backupFile;
                }
            case 18:
            case 24:
            case 48:
        }

        return null;
    }

    private void initConventionDefaults() {
        this._backupDir = Convention.getBackupDirectory();
        this._backupLevel = 0;
        this._holdBackup = false;
        this._state = Jalopy.State.UNDEFINED;
        this._outputFileFormat = FileFormat.UNKNOWN;
        this._destination = null;
        this._encoding = null;
    }

    private void print(JavaNode tree, String packageName, FileFormat format) throws IOException {
        if (this._state != Jalopy.State.ERROR) {
            switch(this._mode) {
                case 3:
                    if (this._destination == null && !this.isChecksum()) {
                        this._backupFile = this.createBackup(packageName);
                    }

                    if (this._destination != null) {
                        this._outputFile = this.getDestinationFile(this._destination, packageName, this._outputFile.getName());
                    }

                    if (!this.isWritable(this._outputFile)) {
                        this._args[0] = this._outputFile.getAbsolutePath();
                        this._outputFile = null;
                        return;
                    }

                    if (!this.isChecksum()) {
                        this._outputWriter = getBufferedWriter(this._outputFile, this._encoding);
                    }
                    break;
                case 6:
                case 18:
                    if (this._destination != null) {
                        this._outputFile = this.getDestinationFile(this._destination, packageName, this._outputFile.getName());
                    }

                    if (!this.isWritable(this._outputFile)) {
                        this._args[0] = this._outputFile.getAbsolutePath();
                        this._outputFile = null;
                        return;
                    }

                    this._outputWriter = getBufferedWriter(this._outputFile, this._encoding);
                case 12:
                case 24:
                case 36:
                case 48:
                    this._backupFile = this.createBackup(packageName);
                    break;
                case 9:
                case 33:
                    this._backupFile = this.createBackup(packageName);
                    break;
                default:
                    throw new IllegalStateException("both input source and output target has to be specified");
            }

            this._now = System.currentTimeMillis();
            Writer outputWriter = null;
            ChecksumCharArrayWriter checksumWriter = null;
            if (this.isChecksum()) {
                checksumWriter = new ChecksumCharArrayWriter(this._historyMethod);
                outputWriter = new BufferedWriter(checksumWriter);
            } else {
                outputWriter = this._outputWriter;
            }

            NodeWriter out = new NodeWriter((Writer)outputWriter, _factory, this._inputFile.getAbsolutePath(), this._issues, this.getLineSeparator(this._outputFileFormat, format), format.toString());
            out.setTracking(this._recognizer.hasAnnotations() || this._recognizer.hasPosition());
            Environment environment = Environment.getInstance().copy();
            this.setLocalVariables(environment, this._inputFile, packageName, this._outputFileFormat.getName(), out.getIndentSize());
            out.setEnvironment(environment);
            this.addCommentHistoryEntry(packageName, out);
            long start = 0L;

            try {
                long stop;
                if (Loggers.IO.isDebugEnabled()) {
                    Loggers.IO.debug((this._outputFile != null ? this._outputFile : this._inputFile) + ":0:0:print");
                    start = System.currentTimeMillis();
                    PrinterFactory.create(tree, out).print(tree, out);
                    if (!this.isChecksum()) {
                        stop = System.currentTimeMillis();
                        this._timePrinting += stop - start;
                        Loggers.IO.debug((this._outputFile != null ? this._outputFile : this._inputFile) + ":0:0:printing took " + (stop - start));
                    }
                } else {
                    PrinterFactory.create(tree, out).print(tree, out);
                }

                if (this.isChecksum()) {
                    out.flush();
                    if (this._inputFileChecksum != null && this._inputFileChecksum.getValue() == checksumWriter.getChecksum().getValue()) {
                    } else {
                        if (this._destination == null) {
                            this._backupFile = this.createBackup(packageName);
                        }

                        this._outputWriter = getBufferedWriter(this._outputFile, this._encoding);
                        checksumWriter.writeTo(this._outputWriter);
                    }

                    if (Loggers.IO.isDebugEnabled()) {
                        stop = System.currentTimeMillis();
                        this._timePrinting += stop - start;
                        Loggers.IO.debug((this._outputFile != null ? this._outputFile : this._inputFile) + ":0:0:printing took " + (stop - start));
                    }
                }

                this.addFileHistoryEntry(packageName, checksumWriter);
            } finally {
                this.unsetLocalVariables(environment);
                if (out != null) {
                    out.close();
                }

                if (this.isChecksum() && this._outputWriter != null) {
                    this._outputWriter.close();
                }

            }

        }
    }

    private static BufferedReader getBufferedReader(File file, String encoding) throws FileNotFoundException {
        try {
            BufferedReader reader;
            if (encoding != null) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            } else {
                reader = new BufferedReader(new FileReader(file));
            }

            return reader;
        } catch (UnsupportedEncodingException var4) {
            throw new FileNotFoundException("Unsupported encoding " + encoding);
        }
    }

    private static BufferedWriter getBufferedWriter(File file, String encoding) throws IOException {
        BufferedWriter writer;
        if (encoding != null) {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
        } else {
            writer = new BufferedWriter(new FileWriter(file));
        }

        return writer;
    }

    private void restore(File original, File backup) {
        if (original != null && backup != null) {
            this._args[0] = original.getAbsolutePath();
            this._args[1] = backup.getAbsolutePath();

            try {
                Copy.file(backup, original, true);
                original.setLastModified(backup.lastModified());
                backup.delete();
            } catch (IOException var4) {
            }
        }

    }

    private void unsetLocalVariables(Environment environment) {
        environment.unset(Variable.FILE_NAME.getName());
        environment.unset(Variable.FILE.getName());
        environment.unset(Variable.PACKAGE.getName());
        environment.unset(Variable.FILE_FORMAT.getName());
        environment.unset(Variable.TAB_SIZE.getName());
    }

    private final class SpyAppender extends AppenderSkeleton implements Appender {
        public SpyAppender() {
            this.name = "JalopySpyAppender";
        }
        private String name;

        public void append(LoggingEvent ev) {


        }

        @Override
        public void addFilter(Filter filter) {

        }

        @Override
        public Filter getFilter() {
            return null;
        }

        @Override
        public void clearFilters() {

        }

        public void close() {
        }

        public synchronized void doAppend(LoggingEvent ev) {
            this.append(ev);
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setErrorHandler(ErrorHandler errorHandler) {

        }

        @Override
        public ErrorHandler getErrorHandler() {
            return null;
        }

        @Override
        public Layout getLayout() {
            return null;
        }

        public boolean requiresLayout() {
            return false;
        }

        protected boolean checkEntryConditions() {
            return true;
        }
    }

    public static final class State {
        public static final Jalopy.State OK = new Jalopy.State("Jalopy.State [ok]");
        public static final Jalopy.State WARN = new Jalopy.State("Jalopy.State [warn]");
        public static final Jalopy.State ERROR = new Jalopy.State("Jalopy.State [error]");
        public static final Jalopy.State PARSED = new Jalopy.State("Jalopy.State [parsed]");
        public static final Jalopy.State INSPECTED = new Jalopy.State("Jalopy.State [inspected]");
        public static final Jalopy.State RUNNING = new Jalopy.State("Jalopy.State [running]");
        public static final Jalopy.State UNDEFINED = new Jalopy.State("Jalopy.State [undefined]");
        final String name;

        private State(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

package structure.common;

import data.Metric;

public interface IStructure {

    public Metric craft();

    public Metric read();

    public Metric insert();

    public Metric detect();

    public Metric save();

}

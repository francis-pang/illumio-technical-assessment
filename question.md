Thank you for your interest in the Engineering position at Illumio. As part of our interview process, we would like to assess your technical skills through a take-home coding exercise.

Please find the details of the exercise below. We ask that you complete the exercise within 48-72 hours, upload it to a GitHub project and share the project link with us. If you need more time, please tell me so I can update the hiring team. This assessment shouldn’t take longer than 2(ish) hours. Feel free to write the code in whatever environment you prefer.

### Description:

Write a program that can parse a file containing flow log data and maps each row to a tag based on a lookup table. The lookup table is defined as a CSV file, and it has 3 columns, `dstport`, `protocol`, `tag`. The `dstport` and `protocol` combination decide what tag can be applied.

For example, the lookup table file can be something like:
```csv
dstport,protocol,tag
25,tcp,sv_P1
68,udp,sv_P2
23,tcp,sv_P1 x
31,udp,SV_P3
443,tcp,sv_P2
```

The program should generate an output file containing the following:
- Count of matches for each tag, sample output shown below

#### Tag Counts:
```
Tag: Count
Untagged 2
sv_P2 2
SV_P3 1
sv_P1 2
```

- Count of matches for each port/protocol combination

#### Port/Protocol Combination Counts:
```
Port. Protocol. Count
23. tcp 1
80. tcp 1
68. udp 1
25. tcp 1
31. udp 1
443. tcp 1
```

### Requirement details:
- Input file as well as the file containing tag mappings are plain text (ascii) files
- The flow log file size can be up to 10 MB
- The lookup file can have up to 10000 mappings
- The tags can map to more than one port, protocol combinations. For example, `sv_P1` and `sv_P2` in the sample above.
- The matches should be case insensitive

For anything else that is not clear, please make reasonable assumptions and document those in the README to be sent with your submission.

Reference for flow logs: [AWS Flow Log Records](https://docs.aws.amazon.com/vpc/latest/userguide/flow-log-records.html)

### Submissions:
Please upload the submission to GitHub and send a link. The submission should come with a README with info on all the assumptions made, how to compile/run the program, what tests were done, and any other analysis that was done.

Please avoid using non-default libraries or packages like Hadoop, Spark, Pandas, etc. The idea is to be able to review and run the program on a local machine without needing to install too many dependencies/packages.

Thanks and best of luck!
TARGET_JAR       = target/instaparse-fuzz-wrapper-0.1.0-SNAPSHOT-standalone.jar
TARGET_CLASSPATH = com.code_intelligence.jazzer.Jazzer
TARGET_CLASS     = instaparse_fuzz_wrapper.targets.first_try

all : build fuzz

build : src/instaparse_fuzz_wrapper/core.clj
	lein uberjar

fuzz : build
	java -cp $(TARGET_JAR) $(TARGET_CLASSPATH) --target_class=$(TARGET_CLASS) \
		--instrumentation_includes='instaparse.**' \
		--ignore=ea655cd7f3abd65c,7090143765e6b69b,d8784df40506d34b \
		inputcorpus

clean-corpus :
	rm inputcorpus/*

clean :
	rm inputcorpus/*
	rm -f target/*

.PHONY: all clean

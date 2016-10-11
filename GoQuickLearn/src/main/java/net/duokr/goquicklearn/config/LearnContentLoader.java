package net.duokr.goquicklearn.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;

public class LearnContentLoader {
	private static String CONTENT_TAG_NAME = "Content";

	private static String CONTENT_NAME = "Name";
	private static String CONTENT_ABSTRACT = "Abstract";
	private static String CONTENT_CHAPTER = "Chapter";

	public static List<LearnContent> loadLearnContentList(int resXmlId,
			Activity activity) {
		List<LearnContent> learnContentList = new ArrayList<LearnContent>();
		XmlResourceParser xrp = activity.getResources().getXml(resXmlId);
		try {
			LearnContent learnContent = null;
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (xrp.getEventType() == XmlResourceParser.START_TAG) {
					String tagName = xrp.getName();

					if (tagName.equals(CONTENT_TAG_NAME)) {
						learnContent = new LearnContent();
					}
					// parse tag elements
					else if (tagName.equals(CONTENT_NAME)) {
						learnContent.setContentName(xrp.nextText());
					} else if (tagName.equals(CONTENT_ABSTRACT)) {
						learnContent.setContentAbstract(xrp.nextText());
					} else if (tagName.equals(CONTENT_CHAPTER)) {
						learnContent.setChapter(xrp.nextText());
					}
				} else if (xrp.getEventType() == XmlResourceParser.END_TAG) {
					String tagName = xrp.getName();
					if (tagName.equals(CONTENT_TAG_NAME)) {
						if (learnContent != null) {
							learnContentList.add(learnContent);
							learnContent = null;
						}
					}
				}

				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return learnContentList;
	}
}

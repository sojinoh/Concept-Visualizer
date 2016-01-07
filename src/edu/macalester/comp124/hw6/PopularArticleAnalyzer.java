package edu.macalester.comp124.hw6;

import org.jooq.util.derby.sys.Sys;
import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.dao.DaoException;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Analyzes the overlap in popular concepts.
 * Experimental code for Shilad's intro Java course.
 * Note that you MUST correct WikAPIdiaWrapper.DATA_DIRECTORY before this works.
 *
 * @author Shilad Sen
 */
public class PopularArticleAnalyzer {
    private final WikAPIdiaWrapper wpApi;

    /**
     * Constructs a new analyzer.
     * @param wpApi
     */
    public PopularArticleAnalyzer(WikAPIdiaWrapper wpApi) {
        this.wpApi = wpApi;
    }

    /**
     * Returns the n most popular articles in the specified language.
     * @param language
     * @param n
     * @return
     */
    public List<LocalPage> getMostPopular(Language language, int n) {
        // TODO: implement me for part 1
        List <LocalPagePopularity> popularityList = new ArrayList<LocalPagePopularity>();
        for(LocalPage lp : wpApi.getLocalPages(language)){
            int num = wpApi.getNumInLinks(lp);
            LocalPagePopularity lpp = new LocalPagePopularity(lp,num);
            popularityList.add(lpp);
        }
        Collections.sort(popularityList);
        Collections.reverse(popularityList);
        List <LocalPage> listReturn = new ArrayList<LocalPage>();
        for(LocalPagePopularity lpPopularity: popularityList){
            if(listReturn.size()==n){
                break;
            }
            listReturn.add(lpPopularity.getPage());

        }
        return listReturn;
    }

    public static void main(String args[]) {
        Language simple = Language.getByLangCode("simple");

        // Change the path below to point to the parent directory on the lab computer
        // or laptop that holds the BIG "db" directory.
        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper();

        // TODO: Complete me for part 1.
        // construct a PopularArticleAnalyzer
        PopularArticleAnalyzer paa = new PopularArticleAnalyzer(wrapper);
        // Print out the 20 most popular articles in the language.
        List <LocalPage> result = paa.getMostPopular(simple,20);
        System.out.println("These are the 20 most popular articles in simple English: ");
        for(LocalPage popularArticle : result){
            System.out.println(popularArticle.getTitle());
        }
        // United states should be #1
    }
}

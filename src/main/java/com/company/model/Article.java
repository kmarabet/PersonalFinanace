package com.company.model;

import com.company.exception.ModelException;
import java.util.Objects;
import com.company.saveload.SaveData;

public class Article extends Common {

    private String title;

    public Article() {
    }

    public Article(String title) throws ModelException {
        if (title.length() == 0) throw new ModelException(ModelException.TITLE_EMPTY);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String getValueForComboBox() {
        return title;
    }

    @Override
    public void postEdit(SaveData sd) {
        for (Transaction t: sd.getTransactions())
            if (t.getArticle().equals(sd.getOldCommon())) t.setArticle(this);
    }

}

package dev.gooiman.server.memo.application.domain;

import dev.gooiman.server.memo.repository.view.MemoSummariesView;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoSummaryList {

    Map<String, Map<String, List<String>>> views;

    public MemoSummaryList(List<MemoSummariesView> views) {
        this.views = views.stream().collect(
            Collectors.groupingBy(MemoSummariesView::getCategory,
                Collectors.groupingBy(MemoSummariesView::getSubCategory,
                    Collectors.mapping(MemoSummariesView::getTitle, Collectors.toList()))));
    }
}

package fr.loicleprieur.utility.string;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Levenshtein {

  /**
   * Search a list of object M by one of their attribute with
   * a Levenshtein distance algorithm
   *
   * @param <M> The type of the objects in the list
   * @param className - Class of the objects
   * @param objetList - Searched objects
   * @param attributeGetterName - name of the getter attribute (e.g. getLabel)
   * @param searchString - String to search for
   * @return A List of M with a Levenshtein distance less than or equal to Math.max(searchString.length() / 4, 1) to the given string
   */
  @SuppressWarnings("unchecked")
  protected static <M> Collection<M> fuzzySearchByAttribute(Class<M> className, List<M> objetList, String attributeGetterName, String searchString) {
      
    if (objetList == null || searchString == null || searchString.isEmpty()) {
      return Collections.emptyList();
    }

    List<Object> closestObjects = objetList
        .stream()
        .filter(o -> {
            try {
                Method getterMethod = o.getClass().getMethod(attributeGetterName);
                Object attribute = getterMethod.invoke(o);
                LevenshteinDistance instance = LevenshteinDistance.getDefaultInstance();
                int dist = instance.apply(searchString, attribute.toString());
                return dist <= Math.max(searchString.length() / 4, 1);
            } catch (Exception e) {
                System.out.println("Error while getting attribute: " + e.getMessage());
                return false;
            }
        })
        .collect(Collectors.toList());

    // Cast to List<M> because the filter method returns a stream of Objects
    return closestObjects.stream().filter(m -> className.isInstance(m)).map(m -> (M)m).collect(Collectors.toList());
  }
}

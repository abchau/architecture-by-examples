(ns archexamples.thymeleaf
  (:import
    (java.util
      Locale)
    (org.thymeleaf
      TemplateEngine)
    (org.thymeleaf.context
      Context)
    (org.thymeleaf.templatemode
      TemplateMode)
    (org.thymeleaf.templateresolver
      ClassLoaderTemplateResolver)))


(defn ->template-engine
  []
  (let [resolver (doto (ClassLoaderTemplateResolver.)
                   (.setTemplateMode TemplateMode/HTML)
                   (.setPrefix "templates/")
                   (.setSuffix ".html")
                   (.setCacheTTLMs (Long/valueOf 3600000))
                   (.setCacheable true))]
    (doto (TemplateEngine.)
      (.setTemplateResolver resolver))))


(defn ->template-context
  [vars]
  (let [ctx (Context. (Locale/getDefault) (some-> vars (update-keys name)))]
    ctx))


(defn render
  [engine template-name context]
  (.process engine template-name (->template-context context)))

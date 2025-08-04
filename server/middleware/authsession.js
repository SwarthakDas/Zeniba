export const authsession = (req, res, next) => {
  if (!req.app.locals.userCredential || !req.app.locals.userCredential.access_token) {
    return res.status(401).json({ message: "Not authenticated" });
  }
  next();
};

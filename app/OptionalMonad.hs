data Optional a = Some a | None deriving (Show, Ord, Eq)

isSome :: Optional a -> Bool
isSome (Some _) = True
isSome None = False

instance Functor Optional where
  fmap f (Some a) = Some (f a)
  fmap f None = None

instance Applicative Optional where
  pure = Some
  None <*> _ = None
  _ <*> None = None
  (Some f) <*> (Some a) = Some (f a)

instance Monad Optional where
  None >>= _ = None
  (Some a) >>= f = f a

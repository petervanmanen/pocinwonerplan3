import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap';
import SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDetail from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-detail';
import SluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdate from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-update';
import SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDeleteDialog from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-delete-dialog';

const SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap />} />
    <Route path="new" element={<SluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdate />} />
    <Route path=":id">
      <Route index element={<SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDetail />} />
      <Route path="edit" element={<SluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdate />} />
      <Route path="delete" element={<SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRoutes;

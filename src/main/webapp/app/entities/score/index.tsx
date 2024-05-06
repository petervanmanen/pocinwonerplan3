import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Score from './score';
import ScoreDetail from './score-detail';
import ScoreUpdate from './score-update';
import ScoreDeleteDialog from './score-delete-dialog';

const ScoreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Score />} />
    <Route path="new" element={<ScoreUpdate />} />
    <Route path=":id">
      <Route index element={<ScoreDetail />} />
      <Route path="edit" element={<ScoreUpdate />} />
      <Route path="delete" element={<ScoreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScoreRoutes;

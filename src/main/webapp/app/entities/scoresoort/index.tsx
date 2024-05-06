import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Scoresoort from './scoresoort';
import ScoresoortDetail from './scoresoort-detail';
import ScoresoortUpdate from './scoresoort-update';
import ScoresoortDeleteDialog from './scoresoort-delete-dialog';

const ScoresoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Scoresoort />} />
    <Route path="new" element={<ScoresoortUpdate />} />
    <Route path=":id">
      <Route index element={<ScoresoortDetail />} />
      <Route path="edit" element={<ScoresoortUpdate />} />
      <Route path="delete" element={<ScoresoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScoresoortRoutes;

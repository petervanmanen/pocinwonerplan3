import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ligplaats from './ligplaats';
import LigplaatsDetail from './ligplaats-detail';
import LigplaatsUpdate from './ligplaats-update';
import LigplaatsDeleteDialog from './ligplaats-delete-dialog';

const LigplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ligplaats />} />
    <Route path="new" element={<LigplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<LigplaatsDetail />} />
      <Route path="edit" element={<LigplaatsUpdate />} />
      <Route path="delete" element={<LigplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LigplaatsRoutes;

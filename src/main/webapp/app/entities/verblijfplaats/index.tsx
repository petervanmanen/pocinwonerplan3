import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfplaats from './verblijfplaats';
import VerblijfplaatsDetail from './verblijfplaats-detail';
import VerblijfplaatsUpdate from './verblijfplaats-update';
import VerblijfplaatsDeleteDialog from './verblijfplaats-delete-dialog';

const VerblijfplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfplaats />} />
    <Route path="new" element={<VerblijfplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfplaatsDetail />} />
      <Route path="edit" element={<VerblijfplaatsUpdate />} />
      <Route path="delete" element={<VerblijfplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfplaatsRoutes;

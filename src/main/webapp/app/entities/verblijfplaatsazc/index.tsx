import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfplaatsazc from './verblijfplaatsazc';
import VerblijfplaatsazcDetail from './verblijfplaatsazc-detail';
import VerblijfplaatsazcUpdate from './verblijfplaatsazc-update';
import VerblijfplaatsazcDeleteDialog from './verblijfplaatsazc-delete-dialog';

const VerblijfplaatsazcRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfplaatsazc />} />
    <Route path="new" element={<VerblijfplaatsazcUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfplaatsazcDetail />} />
      <Route path="edit" element={<VerblijfplaatsazcUpdate />} />
      <Route path="delete" element={<VerblijfplaatsazcDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfplaatsazcRoutes;

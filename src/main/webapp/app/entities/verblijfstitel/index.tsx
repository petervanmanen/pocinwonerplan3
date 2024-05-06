import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfstitel from './verblijfstitel';
import VerblijfstitelDetail from './verblijfstitel-detail';
import VerblijfstitelUpdate from './verblijfstitel-update';
import VerblijfstitelDeleteDialog from './verblijfstitel-delete-dialog';

const VerblijfstitelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfstitel />} />
    <Route path="new" element={<VerblijfstitelUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfstitelDetail />} />
      <Route path="edit" element={<VerblijfstitelUpdate />} />
      <Route path="delete" element={<VerblijfstitelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfstitelRoutes;

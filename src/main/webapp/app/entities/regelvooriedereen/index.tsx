import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Regelvooriedereen from './regelvooriedereen';
import RegelvooriedereenDetail from './regelvooriedereen-detail';
import RegelvooriedereenUpdate from './regelvooriedereen-update';
import RegelvooriedereenDeleteDialog from './regelvooriedereen-delete-dialog';

const RegelvooriedereenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Regelvooriedereen />} />
    <Route path="new" element={<RegelvooriedereenUpdate />} />
    <Route path=":id">
      <Route index element={<RegelvooriedereenDetail />} />
      <Route path="edit" element={<RegelvooriedereenUpdate />} />
      <Route path="delete" element={<RegelvooriedereenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegelvooriedereenRoutes;

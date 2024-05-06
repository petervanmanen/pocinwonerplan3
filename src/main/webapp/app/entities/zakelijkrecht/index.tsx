import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zakelijkrecht from './zakelijkrecht';
import ZakelijkrechtDetail from './zakelijkrecht-detail';
import ZakelijkrechtUpdate from './zakelijkrecht-update';
import ZakelijkrechtDeleteDialog from './zakelijkrecht-delete-dialog';

const ZakelijkrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zakelijkrecht />} />
    <Route path="new" element={<ZakelijkrechtUpdate />} />
    <Route path=":id">
      <Route index element={<ZakelijkrechtDetail />} />
      <Route path="edit" element={<ZakelijkrechtUpdate />} />
      <Route path="delete" element={<ZakelijkrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZakelijkrechtRoutes;

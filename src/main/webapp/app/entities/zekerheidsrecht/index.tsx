import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zekerheidsrecht from './zekerheidsrecht';
import ZekerheidsrechtDetail from './zekerheidsrecht-detail';
import ZekerheidsrechtUpdate from './zekerheidsrecht-update';
import ZekerheidsrechtDeleteDialog from './zekerheidsrecht-delete-dialog';

const ZekerheidsrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zekerheidsrecht />} />
    <Route path="new" element={<ZekerheidsrechtUpdate />} />
    <Route path=":id">
      <Route index element={<ZekerheidsrechtDetail />} />
      <Route path="edit" element={<ZekerheidsrechtUpdate />} />
      <Route path="delete" element={<ZekerheidsrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZekerheidsrechtRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Waarneming from './waarneming';
import WaarnemingDetail from './waarneming-detail';
import WaarnemingUpdate from './waarneming-update';
import WaarnemingDeleteDialog from './waarneming-delete-dialog';

const WaarnemingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Waarneming />} />
    <Route path="new" element={<WaarnemingUpdate />} />
    <Route path=":id">
      <Route index element={<WaarnemingDetail />} />
      <Route path="edit" element={<WaarnemingUpdate />} />
      <Route path="delete" element={<WaarnemingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WaarnemingRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Toewijzing from './toewijzing';
import ToewijzingDetail from './toewijzing-detail';
import ToewijzingUpdate from './toewijzing-update';
import ToewijzingDeleteDialog from './toewijzing-delete-dialog';

const ToewijzingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Toewijzing />} />
    <Route path="new" element={<ToewijzingUpdate />} />
    <Route path=":id">
      <Route index element={<ToewijzingDetail />} />
      <Route path="edit" element={<ToewijzingUpdate />} />
      <Route path="delete" element={<ToewijzingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ToewijzingRoutes;

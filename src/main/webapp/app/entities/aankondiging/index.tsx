import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aankondiging from './aankondiging';
import AankondigingDetail from './aankondiging-detail';
import AankondigingUpdate from './aankondiging-update';
import AankondigingDeleteDialog from './aankondiging-delete-dialog';

const AankondigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aankondiging />} />
    <Route path="new" element={<AankondigingUpdate />} />
    <Route path=":id">
      <Route index element={<AankondigingDetail />} />
      <Route path="edit" element={<AankondigingUpdate />} />
      <Route path="delete" element={<AankondigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AankondigingRoutes;

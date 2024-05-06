import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitnodiging from './uitnodiging';
import UitnodigingDetail from './uitnodiging-detail';
import UitnodigingUpdate from './uitnodiging-update';
import UitnodigingDeleteDialog from './uitnodiging-delete-dialog';

const UitnodigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitnodiging />} />
    <Route path="new" element={<UitnodigingUpdate />} />
    <Route path=":id">
      <Route index element={<UitnodigingDetail />} />
      <Route path="edit" element={<UitnodigingUpdate />} />
      <Route path="delete" element={<UitnodigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitnodigingRoutes;

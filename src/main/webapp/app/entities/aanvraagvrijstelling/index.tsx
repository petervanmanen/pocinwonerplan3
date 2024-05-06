import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraagvrijstelling from './aanvraagvrijstelling';
import AanvraagvrijstellingDetail from './aanvraagvrijstelling-detail';
import AanvraagvrijstellingUpdate from './aanvraagvrijstelling-update';
import AanvraagvrijstellingDeleteDialog from './aanvraagvrijstelling-delete-dialog';

const AanvraagvrijstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraagvrijstelling />} />
    <Route path="new" element={<AanvraagvrijstellingUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagvrijstellingDetail />} />
      <Route path="edit" element={<AanvraagvrijstellingUpdate />} />
      <Route path="delete" element={<AanvraagvrijstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagvrijstellingRoutes;

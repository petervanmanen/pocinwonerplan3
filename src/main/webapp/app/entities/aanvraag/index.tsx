import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraag from './aanvraag';
import AanvraagDetail from './aanvraag-detail';
import AanvraagUpdate from './aanvraag-update';
import AanvraagDeleteDialog from './aanvraag-delete-dialog';

const AanvraagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraag />} />
    <Route path="new" element={<AanvraagUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagDetail />} />
      <Route path="edit" element={<AanvraagUpdate />} />
      <Route path="delete" element={<AanvraagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagRoutes;

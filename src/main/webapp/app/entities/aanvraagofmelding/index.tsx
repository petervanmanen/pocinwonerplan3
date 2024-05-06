import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraagofmelding from './aanvraagofmelding';
import AanvraagofmeldingDetail from './aanvraagofmelding-detail';
import AanvraagofmeldingUpdate from './aanvraagofmelding-update';
import AanvraagofmeldingDeleteDialog from './aanvraagofmelding-delete-dialog';

const AanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraagofmelding />} />
    <Route path="new" element={<AanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagofmeldingDetail />} />
      <Route path="edit" element={<AanvraagofmeldingUpdate />} />
      <Route path="delete" element={<AanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagofmeldingRoutes;

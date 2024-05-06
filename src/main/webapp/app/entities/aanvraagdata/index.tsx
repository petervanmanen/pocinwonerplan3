import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraagdata from './aanvraagdata';
import AanvraagdataDetail from './aanvraagdata-detail';
import AanvraagdataUpdate from './aanvraagdata-update';
import AanvraagdataDeleteDialog from './aanvraagdata-delete-dialog';

const AanvraagdataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraagdata />} />
    <Route path="new" element={<AanvraagdataUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagdataDetail />} />
      <Route path="edit" element={<AanvraagdataUpdate />} />
      <Route path="delete" element={<AanvraagdataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagdataRoutes;

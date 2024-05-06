import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraagstadspas from './aanvraagstadspas';
import AanvraagstadspasDetail from './aanvraagstadspas-detail';
import AanvraagstadspasUpdate from './aanvraagstadspas-update';
import AanvraagstadspasDeleteDialog from './aanvraagstadspas-delete-dialog';

const AanvraagstadspasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraagstadspas />} />
    <Route path="new" element={<AanvraagstadspasUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagstadspasDetail />} />
      <Route path="edit" element={<AanvraagstadspasUpdate />} />
      <Route path="delete" element={<AanvraagstadspasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagstadspasRoutes;

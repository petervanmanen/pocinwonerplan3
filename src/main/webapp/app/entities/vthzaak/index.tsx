import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vthzaak from './vthzaak';
import VthzaakDetail from './vthzaak-detail';
import VthzaakUpdate from './vthzaak-update';
import VthzaakDeleteDialog from './vthzaak-delete-dialog';

const VthzaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vthzaak />} />
    <Route path="new" element={<VthzaakUpdate />} />
    <Route path=":id">
      <Route index element={<VthzaakDetail />} />
      <Route path="edit" element={<VthzaakUpdate />} />
      <Route path="delete" element={<VthzaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VthzaakRoutes;

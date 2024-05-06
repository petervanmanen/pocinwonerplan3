import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zaak from './zaak';
import ZaakDetail from './zaak-detail';
import ZaakUpdate from './zaak-update';
import ZaakDeleteDialog from './zaak-delete-dialog';

const ZaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zaak />} />
    <Route path="new" element={<ZaakUpdate />} />
    <Route path=":id">
      <Route index element={<ZaakDetail />} />
      <Route path="edit" element={<ZaakUpdate />} />
      <Route path="delete" element={<ZaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZaakRoutes;

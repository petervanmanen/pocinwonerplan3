import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kenmerkenzaak from './kenmerkenzaak';
import KenmerkenzaakDetail from './kenmerkenzaak-detail';
import KenmerkenzaakUpdate from './kenmerkenzaak-update';
import KenmerkenzaakDeleteDialog from './kenmerkenzaak-delete-dialog';

const KenmerkenzaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kenmerkenzaak />} />
    <Route path="new" element={<KenmerkenzaakUpdate />} />
    <Route path=":id">
      <Route index element={<KenmerkenzaakDetail />} />
      <Route path="edit" element={<KenmerkenzaakUpdate />} />
      <Route path="delete" element={<KenmerkenzaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KenmerkenzaakRoutes;

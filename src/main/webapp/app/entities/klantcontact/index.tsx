import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Klantcontact from './klantcontact';
import KlantcontactDetail from './klantcontact-detail';
import KlantcontactUpdate from './klantcontact-update';
import KlantcontactDeleteDialog from './klantcontact-delete-dialog';

const KlantcontactRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Klantcontact />} />
    <Route path="new" element={<KlantcontactUpdate />} />
    <Route path=":id">
      <Route index element={<KlantcontactDetail />} />
      <Route path="edit" element={<KlantcontactUpdate />} />
      <Route path="delete" element={<KlantcontactDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KlantcontactRoutes;

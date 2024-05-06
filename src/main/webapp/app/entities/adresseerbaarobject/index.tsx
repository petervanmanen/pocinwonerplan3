import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Adresseerbaarobject from './adresseerbaarobject';
import AdresseerbaarobjectDetail from './adresseerbaarobject-detail';
import AdresseerbaarobjectUpdate from './adresseerbaarobject-update';
import AdresseerbaarobjectDeleteDialog from './adresseerbaarobject-delete-dialog';

const AdresseerbaarobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Adresseerbaarobject />} />
    <Route path="new" element={<AdresseerbaarobjectUpdate />} />
    <Route path=":id">
      <Route index element={<AdresseerbaarobjectDetail />} />
      <Route path="edit" element={<AdresseerbaarobjectUpdate />} />
      <Route path="delete" element={<AdresseerbaarobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdresseerbaarobjectRoutes;

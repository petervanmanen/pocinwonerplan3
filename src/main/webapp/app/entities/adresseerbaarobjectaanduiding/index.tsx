import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Adresseerbaarobjectaanduiding from './adresseerbaarobjectaanduiding';
import AdresseerbaarobjectaanduidingDetail from './adresseerbaarobjectaanduiding-detail';
import AdresseerbaarobjectaanduidingUpdate from './adresseerbaarobjectaanduiding-update';
import AdresseerbaarobjectaanduidingDeleteDialog from './adresseerbaarobjectaanduiding-delete-dialog';

const AdresseerbaarobjectaanduidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Adresseerbaarobjectaanduiding />} />
    <Route path="new" element={<AdresseerbaarobjectaanduidingUpdate />} />
    <Route path=":id">
      <Route index element={<AdresseerbaarobjectaanduidingDetail />} />
      <Route path="edit" element={<AdresseerbaarobjectaanduidingUpdate />} />
      <Route path="delete" element={<AdresseerbaarobjectaanduidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdresseerbaarobjectaanduidingRoutes;

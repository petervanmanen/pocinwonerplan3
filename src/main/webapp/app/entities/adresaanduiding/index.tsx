import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Adresaanduiding from './adresaanduiding';
import AdresaanduidingDetail from './adresaanduiding-detail';
import AdresaanduidingUpdate from './adresaanduiding-update';
import AdresaanduidingDeleteDialog from './adresaanduiding-delete-dialog';

const AdresaanduidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Adresaanduiding />} />
    <Route path="new" element={<AdresaanduidingUpdate />} />
    <Route path=":id">
      <Route index element={<AdresaanduidingDetail />} />
      <Route path="edit" element={<AdresaanduidingUpdate />} />
      <Route path="delete" element={<AdresaanduidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdresaanduidingRoutes;

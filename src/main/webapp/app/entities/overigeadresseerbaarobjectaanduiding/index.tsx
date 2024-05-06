import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overigeadresseerbaarobjectaanduiding from './overigeadresseerbaarobjectaanduiding';
import OverigeadresseerbaarobjectaanduidingDetail from './overigeadresseerbaarobjectaanduiding-detail';
import OverigeadresseerbaarobjectaanduidingUpdate from './overigeadresseerbaarobjectaanduiding-update';
import OverigeadresseerbaarobjectaanduidingDeleteDialog from './overigeadresseerbaarobjectaanduiding-delete-dialog';

const OverigeadresseerbaarobjectaanduidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overigeadresseerbaarobjectaanduiding />} />
    <Route path="new" element={<OverigeadresseerbaarobjectaanduidingUpdate />} />
    <Route path=":id">
      <Route index element={<OverigeadresseerbaarobjectaanduidingDetail />} />
      <Route path="edit" element={<OverigeadresseerbaarobjectaanduidingUpdate />} />
      <Route path="delete" element={<OverigeadresseerbaarobjectaanduidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverigeadresseerbaarobjectaanduidingRoutes;

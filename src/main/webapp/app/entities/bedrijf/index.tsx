import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bedrijf from './bedrijf';
import BedrijfDetail from './bedrijf-detail';
import BedrijfUpdate from './bedrijf-update';
import BedrijfDeleteDialog from './bedrijf-delete-dialog';

const BedrijfRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bedrijf />} />
    <Route path="new" element={<BedrijfUpdate />} />
    <Route path=":id">
      <Route index element={<BedrijfDetail />} />
      <Route path="edit" element={<BedrijfUpdate />} />
      <Route path="delete" element={<BedrijfDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BedrijfRoutes;

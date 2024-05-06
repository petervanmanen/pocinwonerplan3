import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Splitsingstekeningreferentie from './splitsingstekeningreferentie';
import SplitsingstekeningreferentieDetail from './splitsingstekeningreferentie-detail';
import SplitsingstekeningreferentieUpdate from './splitsingstekeningreferentie-update';
import SplitsingstekeningreferentieDeleteDialog from './splitsingstekeningreferentie-delete-dialog';

const SplitsingstekeningreferentieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Splitsingstekeningreferentie />} />
    <Route path="new" element={<SplitsingstekeningreferentieUpdate />} />
    <Route path=":id">
      <Route index element={<SplitsingstekeningreferentieDetail />} />
      <Route path="edit" element={<SplitsingstekeningreferentieUpdate />} />
      <Route path="delete" element={<SplitsingstekeningreferentieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SplitsingstekeningreferentieRoutes;

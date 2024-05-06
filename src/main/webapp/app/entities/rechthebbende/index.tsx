import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rechthebbende from './rechthebbende';
import RechthebbendeDetail from './rechthebbende-detail';
import RechthebbendeUpdate from './rechthebbende-update';
import RechthebbendeDeleteDialog from './rechthebbende-delete-dialog';

const RechthebbendeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rechthebbende />} />
    <Route path="new" element={<RechthebbendeUpdate />} />
    <Route path=":id">
      <Route index element={<RechthebbendeDetail />} />
      <Route path="edit" element={<RechthebbendeUpdate />} />
      <Route path="delete" element={<RechthebbendeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RechthebbendeRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pvt from './pvt';
import PvtDetail from './pvt-detail';
import PvtUpdate from './pvt-update';
import PvtDeleteDialog from './pvt-delete-dialog';

const PvtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pvt />} />
    <Route path="new" element={<PvtUpdate />} />
    <Route path=":id">
      <Route index element={<PvtDetail />} />
      <Route path="edit" element={<PvtUpdate />} />
      <Route path="delete" element={<PvtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PvtRoutes;

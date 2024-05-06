import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beschermdestatus from './beschermdestatus';
import BeschermdestatusDetail from './beschermdestatus-detail';
import BeschermdestatusUpdate from './beschermdestatus-update';
import BeschermdestatusDeleteDialog from './beschermdestatus-delete-dialog';

const BeschermdestatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beschermdestatus />} />
    <Route path="new" element={<BeschermdestatusUpdate />} />
    <Route path=":id">
      <Route index element={<BeschermdestatusDetail />} />
      <Route path="edit" element={<BeschermdestatusUpdate />} />
      <Route path="delete" element={<BeschermdestatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeschermdestatusRoutes;

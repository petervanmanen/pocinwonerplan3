import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beheerobject from './beheerobject';
import BeheerobjectDetail from './beheerobject-detail';
import BeheerobjectUpdate from './beheerobject-update';
import BeheerobjectDeleteDialog from './beheerobject-delete-dialog';

const BeheerobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beheerobject />} />
    <Route path="new" element={<BeheerobjectUpdate />} />
    <Route path=":id">
      <Route index element={<BeheerobjectDetail />} />
      <Route path="edit" element={<BeheerobjectUpdate />} />
      <Route path="delete" element={<BeheerobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeheerobjectRoutes;

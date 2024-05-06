import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vervoersmiddel from './vervoersmiddel';
import VervoersmiddelDetail from './vervoersmiddel-detail';
import VervoersmiddelUpdate from './vervoersmiddel-update';
import VervoersmiddelDeleteDialog from './vervoersmiddel-delete-dialog';

const VervoersmiddelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vervoersmiddel />} />
    <Route path="new" element={<VervoersmiddelUpdate />} />
    <Route path=":id">
      <Route index element={<VervoersmiddelDetail />} />
      <Route path="edit" element={<VervoersmiddelUpdate />} />
      <Route path="delete" element={<VervoersmiddelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VervoersmiddelRoutes;

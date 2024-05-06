import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hoofdstuk from './hoofdstuk';
import HoofdstukDetail from './hoofdstuk-detail';
import HoofdstukUpdate from './hoofdstuk-update';
import HoofdstukDeleteDialog from './hoofdstuk-delete-dialog';

const HoofdstukRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hoofdstuk />} />
    <Route path="new" element={<HoofdstukUpdate />} />
    <Route path=":id">
      <Route index element={<HoofdstukDetail />} />
      <Route path="edit" element={<HoofdstukUpdate />} />
      <Route path="delete" element={<HoofdstukDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoofdstukRoutes;

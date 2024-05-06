import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Normwaarde from './normwaarde';
import NormwaardeDetail from './normwaarde-detail';
import NormwaardeUpdate from './normwaarde-update';
import NormwaardeDeleteDialog from './normwaarde-delete-dialog';

const NormwaardeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Normwaarde />} />
    <Route path="new" element={<NormwaardeUpdate />} />
    <Route path=":id">
      <Route index element={<NormwaardeDetail />} />
      <Route path="edit" element={<NormwaardeUpdate />} />
      <Route path="delete" element={<NormwaardeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NormwaardeRoutes;

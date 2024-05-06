import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Balieverkoopentreekaart from './balieverkoopentreekaart';
import BalieverkoopentreekaartDetail from './balieverkoopentreekaart-detail';
import BalieverkoopentreekaartUpdate from './balieverkoopentreekaart-update';
import BalieverkoopentreekaartDeleteDialog from './balieverkoopentreekaart-delete-dialog';

const BalieverkoopentreekaartRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Balieverkoopentreekaart />} />
    <Route path="new" element={<BalieverkoopentreekaartUpdate />} />
    <Route path=":id">
      <Route index element={<BalieverkoopentreekaartDetail />} />
      <Route path="edit" element={<BalieverkoopentreekaartUpdate />} />
      <Route path="delete" element={<BalieverkoopentreekaartDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BalieverkoopentreekaartRoutes;

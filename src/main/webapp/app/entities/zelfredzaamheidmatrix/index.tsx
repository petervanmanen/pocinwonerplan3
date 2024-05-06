import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zelfredzaamheidmatrix from './zelfredzaamheidmatrix';
import ZelfredzaamheidmatrixDetail from './zelfredzaamheidmatrix-detail';
import ZelfredzaamheidmatrixUpdate from './zelfredzaamheidmatrix-update';
import ZelfredzaamheidmatrixDeleteDialog from './zelfredzaamheidmatrix-delete-dialog';

const ZelfredzaamheidmatrixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zelfredzaamheidmatrix />} />
    <Route path="new" element={<ZelfredzaamheidmatrixUpdate />} />
    <Route path=":id">
      <Route index element={<ZelfredzaamheidmatrixDetail />} />
      <Route path="edit" element={<ZelfredzaamheidmatrixUpdate />} />
      <Route path="delete" element={<ZelfredzaamheidmatrixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZelfredzaamheidmatrixRoutes;

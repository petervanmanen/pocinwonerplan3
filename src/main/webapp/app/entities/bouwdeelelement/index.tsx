import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwdeelelement from './bouwdeelelement';
import BouwdeelelementDetail from './bouwdeelelement-detail';
import BouwdeelelementUpdate from './bouwdeelelement-update';
import BouwdeelelementDeleteDialog from './bouwdeelelement-delete-dialog';

const BouwdeelelementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwdeelelement />} />
    <Route path="new" element={<BouwdeelelementUpdate />} />
    <Route path=":id">
      <Route index element={<BouwdeelelementDetail />} />
      <Route path="edit" element={<BouwdeelelementUpdate />} />
      <Route path="delete" element={<BouwdeelelementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwdeelelementRoutes;

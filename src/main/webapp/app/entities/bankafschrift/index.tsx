import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bankafschrift from './bankafschrift';
import BankafschriftDetail from './bankafschrift-detail';
import BankafschriftUpdate from './bankafschrift-update';
import BankafschriftDeleteDialog from './bankafschrift-delete-dialog';

const BankafschriftRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bankafschrift />} />
    <Route path="new" element={<BankafschriftUpdate />} />
    <Route path=":id">
      <Route index element={<BankafschriftDetail />} />
      <Route path="edit" element={<BankafschriftUpdate />} />
      <Route path="delete" element={<BankafschriftDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankafschriftRoutes;

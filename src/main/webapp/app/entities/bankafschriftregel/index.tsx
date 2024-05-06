import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bankafschriftregel from './bankafschriftregel';
import BankafschriftregelDetail from './bankafschriftregel-detail';
import BankafschriftregelUpdate from './bankafschriftregel-update';
import BankafschriftregelDeleteDialog from './bankafschriftregel-delete-dialog';

const BankafschriftregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bankafschriftregel />} />
    <Route path="new" element={<BankafschriftregelUpdate />} />
    <Route path=":id">
      <Route index element={<BankafschriftregelDetail />} />
      <Route path="edit" element={<BankafschriftregelUpdate />} />
      <Route path="delete" element={<BankafschriftregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankafschriftregelRoutes;

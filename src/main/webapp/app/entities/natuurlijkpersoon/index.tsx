import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Natuurlijkpersoon from './natuurlijkpersoon';
import NatuurlijkpersoonDetail from './natuurlijkpersoon-detail';
import NatuurlijkpersoonUpdate from './natuurlijkpersoon-update';
import NatuurlijkpersoonDeleteDialog from './natuurlijkpersoon-delete-dialog';

const NatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Natuurlijkpersoon />} />
    <Route path="new" element={<NatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NatuurlijkpersoonRoutes;

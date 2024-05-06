import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Samengesteldenaamnatuurlijkpersoon from './samengesteldenaamnatuurlijkpersoon';
import SamengesteldenaamnatuurlijkpersoonDetail from './samengesteldenaamnatuurlijkpersoon-detail';
import SamengesteldenaamnatuurlijkpersoonUpdate from './samengesteldenaamnatuurlijkpersoon-update';
import SamengesteldenaamnatuurlijkpersoonDeleteDialog from './samengesteldenaamnatuurlijkpersoon-delete-dialog';

const SamengesteldenaamnatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Samengesteldenaamnatuurlijkpersoon />} />
    <Route path="new" element={<SamengesteldenaamnatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<SamengesteldenaamnatuurlijkpersoonDetail />} />
      <Route path="edit" element={<SamengesteldenaamnatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<SamengesteldenaamnatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SamengesteldenaamnatuurlijkpersoonRoutes;
